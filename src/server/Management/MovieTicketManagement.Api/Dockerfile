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
ENV ASPNETCORE_HTTP_PORTS=80
WORKDIR /src
COPY ["src/server/Management/MovieTicketManagement.Api/MovieTicketManagement.Api.csproj", "src/server/Management/MovieTicketManagement.Api/"]
COPY ["src/server/BuildingBlock/MovieTicket.Infrastructure/MovieTicket.Infrastructure.csproj", "src/server/BuildingBlock/MovieTicket.Infrastructure/"]
COPY ["src/server/BuildingBlock/MovieTicket.Core/MovieTicket.Core.csproj", "src/server/BuildingBlock/MovieTicket.Core/"]
COPY ["src/server/Management/MovieTicketManagement.Application/MovieTicketManagement.Application.csproj", "src/server/Management/MovieTicketManagement.Application/"]
COPY ["src/server/BuildingBlock/MovieTicket.Domain/MovieTicket.Domain.csproj", "src/server/BuildingBlock/MovieTicket.Domain/"]
COPY ["src/server/BuildingBlock/MovieTicket.Infrastructure.EfCore/MovieTicket.Infrastructure.EfCore.csproj", "src/server/BuildingBlock/MovieTicket.Infrastructure.EfCore/"]
RUN dotnet restore "src/server/Management/MovieTicketManagement.Api/MovieTicketManagement.Api.csproj"
COPY . .
WORKDIR "/src/src/server/Management/MovieTicketManagement.Api"
RUN dotnet build "MovieTicketManagement.Api.csproj" -c $BUILD_CONFIGURATION -o /app/build


COPY --from=node-build /app/dist /src/src/server/Management/MovieTicketManagement.Api/wwwroot


FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicketManagement.Api.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicketManagement.Api.dll"]
