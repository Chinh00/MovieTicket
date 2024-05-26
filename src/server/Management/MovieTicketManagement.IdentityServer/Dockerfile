FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS base
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80

FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
ARG BUILD_CONFIGURATION=Release
ENV ASPNETCORE_HTTP_PORTS=80
WORKDIR /src
COPY ["src/server/Management/MovieTicketManagement.IndentityServer/MovieTicketManagement.IndentityServer.csproj", "src/server/Management/MovieTicketManagement.IndentityServer/"]
RUN dotnet restore "src/server/Management/MovieTicketManagement.IndentityServer/MovieTicketManagement.IndentityServer.csproj"
COPY . .
WORKDIR "/src/src/server/Management/MovieTicketManagement.IndentityServer"
RUN dotnet build "MovieTicketManagement.IndentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "MovieTicketManagement.IndentityServer.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_HTTP_PORTS=80
EXPOSE 80
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "MovieTicketManagement.IndentityServer.dll"]
