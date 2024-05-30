FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
ENV ASPNETCORE_HTTP_PORTS=80
WORKDIR /src
COPY ["src/server/Management/MovieTicketManagement.IdentityServer/MovieTicketManagement.IdentityServer.csproj", "src/server/Management/MovieTicketManagement.IdentityServer/"]
RUN dotnet restore "src/server/Management/MovieTicketManagement.IdentityServer/MovieTicketManagement.IdentityServer.csproj"
COPY . .
WORKDIR "/src/src/server/Management/MovieTicketManagement.IdentityServer"
RUN dotnet build "MovieTicketManagement.IdentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicketManagement.IdentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicketManagement.IdentityServer.dll"]
