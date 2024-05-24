FROM node:lts-alpine AS node-build
WORKDIR /app
COPY ["src/client/admin/", "./"]
RUN yarn install
RUN ls -l /app
RUN yarn run build


FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ["src/server/Client/MovieTicketClient.Api/MovieTicketClient.Api.csproj", "src/server/Client/MovieTicketClient.Api/"]
COPY ["src/server/BuildingBlock/MovieTicket.Infrastructure/MovieTicket.Infrastructure.csproj", "src/server/BuildingBlock/MovieTicket.Infrastructure/"]
COPY ["src/server/BuildingBlock/MovieTicket.Core/MovieTicket.Core.csproj", "src/server/BuildingBlock/MovieTicket.Core/"]
COPY ["src/server/BuildingBlock/MovieTicket.Domain/MovieTicket.Domain.csproj", "src/server/BuildingBlock/MovieTicket.Domain/"]
COPY ["src/server/Client/MovieTicketClient.Application/MovieTicketClient.Application.csproj", "src/server/Client/MovieTicketClient.Application/"]
COPY ["src/server/BuildingBlock/MovieTicket.Infrastructure.EfCore/MovieTicket.Infrastructure.EfCore.csproj", "src/server/BuildingBlock/MovieTicket.Infrastructure.EfCore/"]
RUN dotnet restore "src/server/Client/MovieTicketClient.Api/MovieTicketClient.Api.csproj"
COPY . .

RUN dotnet build "/src/src/server/Client/MovieTicketClient.Api/MovieTicketClient.Api.csproj" -c $BUILD_CONFIGURATION -o /app/build

COPY --from=node-build /app/dist /src/src/server/Client/MovieTicketClient.Api/wwwroot


FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "/src/src/server/Client/MovieTicketClient.Api/MovieTicketClient.Api.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicketClient.Api.dll"]
