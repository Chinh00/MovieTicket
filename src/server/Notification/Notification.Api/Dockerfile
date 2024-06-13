FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80


FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
ENV ASPNETCORE_HTTP_PORTS=80
WORKDIR /src
COPY ["src/server/Notification/Notification.Api/Notification.Api.csproj", "src/server/Notification/Notification.Api/"]
COPY ["src/server/BuildingBlock/MovieTicket.Infrastructure/MovieTicket.Infrastructure.csproj", "src/server/BuildingBlock/MovieTicket.Infrastructure/"]
COPY ["src/server/BuildingBlock/MovieTicket.Core/MovieTicket.Core.csproj", "src/server/BuildingBlock/MovieTicket.Core/"]
COPY ["src/server/Notification/Notification.Application/Notification.Application.csproj", "src/server/Notification/Notification.Application/"]
COPY ["src/server/Notification/Notification.Infrastructure/Notification.Infrastructure.csproj", "src/server/Notification/Notification.Infrastructure/"]
COPY ["src/server/BuildingBlock/MovieTicket.Message/MovieTicket.Message.csproj", "src/server/BuildingBlock/MovieTicket.Message/"]
COPY ["src/server/BuildingBlock/MovieTicket.Domain/MovieTicket.Domain.csproj", "src/server/BuildingBlock/MovieTicket.Domain/"]
RUN dotnet restore "src/server/Notification/Notification.Api/Notification.Api.csproj"
COPY . .
WORKDIR "/src/src/server/Notification/Notification.Api"
RUN dotnet build "Notification.Api.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "Notification.Api.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Notification.Api.dll"]
