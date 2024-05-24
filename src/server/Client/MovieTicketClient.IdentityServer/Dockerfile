FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80


FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["src/server/Client/MovieTicketClient.IdentityServer/MovieTicketClient.IdentityServer.csproj", "src/server/Client/MovieTicketClient.IdentityServer/"]
RUN dotnet restore "src/server/Client/MovieTicketClient.IdentityServer/MovieTicketClient.IdentityServer.csproj"
COPY . .
WORKDIR "/src/src/server/Client/MovieTicketClient.IdentityServer"
RUN dotnet build "MovieTicketClient.IdentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicketClient.IdentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicketClient.IdentityServer.dll"]
