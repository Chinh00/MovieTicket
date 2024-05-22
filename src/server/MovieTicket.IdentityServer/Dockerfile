FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
EXPOSE 8080
EXPOSE 8081

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["src/server/MovieTicket.IdentityServer/MovieTicket.IdentityServer.csproj", "src/server/MovieTicket.IdentityServer/"]
RUN dotnet restore "src/server/MovieTicket.IdentityServer/MovieTicket.IdentityServer.csproj"
COPY . .
WORKDIR "/src/src/server/MovieTicket.IdentityServer"
RUN dotnet build "MovieTicket.IdentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicket.IdentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicket.IdentityServer.dll"]
