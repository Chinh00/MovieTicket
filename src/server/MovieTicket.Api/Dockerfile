FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
USER $APP_UID
WORKDIR /app
EXPOSE 8080
EXPOSE 8081

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["src/server/MovieTicket.Api/MovieTicket.Api.csproj", "src/server/MovieTicket.Api/"]
COPY ["src/server/MovieTicket.Infrastructure.EfCore/MovieTicket.Infrastructure.EfCore.csproj", "src/server/MovieTicket.Infrastructure.EfCore/"]
COPY ["src/server/MovieTicket.Core/MovieTicket.Core.csproj", "src/server/MovieTicket.Core/"]
COPY ["src/server/MovieTicket.Domain/MovieTicket.Domain.csproj", "src/server/MovieTicket.Domain/"]
COPY ["src/server/MovieTicket.Infrastructure/MovieTicket.Infrastructure.csproj", "src/server/MovieTicket.Infrastructure/"]
COPY ["src/server/MovieTicket.Application/MovieTicket.Application.csproj", "src/server/MovieTicket.Application/"]
RUN dotnet restore "src/server/MovieTicket.Api/MovieTicket.Api.csproj"
COPY . .
WORKDIR "/src/src/server/MovieTicket.Api"
RUN dotnet build "MovieTicket.Api.csproj" -c $BUILD_CONFIGURATION -o /app/build


RUN "ls -l"


FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicket.Api.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false



FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicket.Api.dll"]
