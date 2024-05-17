FROM node:lts-alpine AS node-build
WORKDIR /app
COPY ["src/client/admin/", "./"]
RUN yarn install
RUN ls -l /app
RUN yarn run build


FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
ENV ASPNETCORE_HTTP_PORTS=80
WORKDIR /app


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
RUN dotnet build "src/server/MovieTicket.Api/MovieTicket.Api.csproj" -c $BUILD_CONFIGURATION -o /app/build

COPY --from=node-build /app/dist src/server/MovieTicket.Api/wwwroot
RUN ls -l src/server/MovieTicket.Api/wwwroot


FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "src/server/MovieTicket.Api/MovieTicket.Api.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false



FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicket.Api.dll"]
