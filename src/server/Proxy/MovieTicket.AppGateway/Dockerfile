FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
USER $APP_UID
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
ENV ASPNETCORE_HTTP_PORTS=80
WORKDIR /src
COPY ["src/server/Proxy/MovieTicket.AppGateway/MovieTicket.AppGateway.csproj", "src/server/Proxy/MovieTicket.AppGateway/"]
RUN dotnet restore "src/server/Proxy/MovieTicket.AppGateway/MovieTicket.AppGateway.csproj"
COPY . .
WORKDIR "/src/src/server/Proxy/MovieTicket.AppGateway"
RUN dotnet build "MovieTicket.AppGateway.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicket.AppGateway.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicket.AppGateway.dll"]
