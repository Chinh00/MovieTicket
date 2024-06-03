using EasyCaching.Core.Configurations;
using EasyCaching.InMemory;
using EFCoreSecondLevelCacheInterceptor;
using MessagePack;
using MessagePack.Formatters;
using MessagePack.Resolvers;

namespace MovieTicket.Infrastructure.Caching;

public static class Extensions
{
    public static void AddCachingService(this IServiceCollection services,
        IConfiguration configuration)
    {
        var cacheOption = configuration.GetSection("CacheOption").Value;

        services.AddEasyCaching(option =>
        {
            switch (cacheOption)
            {
                case "Redis":
                {
                    var serializerName = configuration.GetSection("Redis:SerializerName").Value;
                    var serverEndPoint = new ServerEndPoint(configuration.GetSection("Redis:Host").Value,
                        int.Parse(configuration.GetSection("Redis:Port").Value!));
                    option.UseRedis(config =>
                    {
                        config.DBConfig.AllowAdmin = true;
                        config.DBConfig.SyncTimeout = 10000;
                        config.DBConfig.AsyncTimeout = 10000;
                        config.DBConfig.Endpoints.Add(serverEndPoint);
                        config.EnableLogging = true;
                        config.SerializerName = serializerName;
                        config.DBConfig.ConnectionTimeout = 10000;
                    }, cacheOption);

                    option.WithMessagePack(so =>
                    {
                        so.EnableCustomResolver = true;
                        so.CustomResolvers = CompositeResolver.Create(
                            new IMessagePackFormatter[]
                            {
                                DbNullFormatter.Instance // This is necessary for the null values
                            },
                            new IFormatterResolver[]
                            {
                                NativeDateTimeResolver.Instance,
                                ContractlessStandardResolver.Instance,
                                StandardResolverAllowPrivate.Instance,
                                TypelessContractlessStandardResolver.Instance,
                                DynamicGenericResolver.Instance
                            });
                    }, serializerName);
                    break;
                }
                case "InMemory":
                    option.UseInMemory(config =>
                    {
                        config.DBConfig = new InMemoryCachingOptions
                        {
                            ExpirationScanFrequency = 60,
                            SizeLimit = 100,
                            EnableReadDeepClone = false,
                            EnableWriteDeepClone = false
                        };
                        config.MaxRdSecond = 120;
                        config.EnableLogging = true;
                        config.LockMs = 5000;
                        config.SleepMs = 300;
                    }, cacheOption);
                    break;
            }
        });

        var cachedTimeSpan = TimeSpan.FromDays(1);

        services.AddEFSecondLevelCache(options =>
            options
                .UseEasyCachingCoreProvider(cacheOption!)
                .CacheAllQueriesExceptContainingTableNames(CacheExpirationMode.Sliding, cachedTimeSpan,
                    "__EFMigrationsHistory")
                .SkipCachingCommands(cmdText => cmdText.Contains("EXISTS"))
        );
    }
    private class DbNullFormatter : IMessagePackFormatter<DBNull>
    {
        public static readonly DbNullFormatter Instance = new();

        private DbNullFormatter()
        {
        }

        /// <inheritdoc />
        public void Serialize(ref MessagePackWriter writer, DBNull value, MessagePackSerializerOptions options)
        {
            writer.WriteNil();
        }

        /// <inheritdoc />
        public DBNull Deserialize(ref MessagePackReader reader, MessagePackSerializerOptions options)
        {
            return DBNull.Value;
        }
    }
}