version: "3.7"

services:
  redis:
    image: redis/redis-stack:latest
    ports:
      - "6379:6379"
      - "8001:8001"
    restart: always
    networks:
      movie-network:
      
  db:
    image: mcr.microsoft.com/mssql/server:2022-latest
    ports:
      - "1435:1433"
    environment:
      SA_PASSWORD: "@P@ssw0rd02"
      ACCEPT_EULA: "Y"
      MSSQL_PID: "Express"
    volumes:
      - data:/var/opt/mssql
    networks:
      movie-network:

  movieticketclient:
    image: chinhval/movieticketclientapi
    environment:
      - ConnectionStrings__db=Server=db;Database=movie_ticket;Encrypt=false;User Id=sa;Password=@P@ssw0rd02;
      - Identity__Url=http://identityserverclient:8091
      - CacheOption=Redis
      - Redis__Host=redis
      - Redis_Port=6379
      - Redis_SerializerName=Pack
    ports:
      - "8090:80"
    depends_on:
      - db
      - redis
      - identityserverclient
    labels:
      - "com.centurylinklabs.watchtower.enbale=true"
    networks:
      movie-network:

  identityserverclient:
    image: chinhval/movieticketmanagementidentity
    environment:
      - ConnectionStrings__db=Server=db;Database=movie_ticket;Encrypt=false;User Id=sa;Password=@P@ssw0rd02;
    ports:
      - "8091:80"
    depends_on:
      - db
    
    labels:
      - "com.centurylinklabs.watchtower.enbale=true"
    networks:
      movie-network:

  movieticketmanagement:
    image: chinhval/movieticketmanagementapi
    environment:
      - ConnectionStrings__db=Server=db;Database=movie_ticket;Encrypt=false;User Id=sa;Password=@P@ssw0rd02;
      - Identity__Url=http://identityservermanagement:8094
      - CacheOption=Redis
      - Redis__Host=redis
      - Redis_Port=6379
      - Redis_SerializerName=Pack
    ports:
      - "8093:80"
    depends_on:
      - db
      - redis
      - identityservermanagement
    
    labels:
      - "com.centurylinklabs.watchtower.enbale=true"
    networks:
      movie-network:

  identityservermanagement:
    image: chinhval/movieticketmanagementidentity
    environment:
      - ConnectionStrings__db=Server=db;Database=movie_admin;Encrypt=false;User Id=sa;Password=@P@ssw0rd02;
    ports:
      - "8094:80"
    depends_on:
      - db
    
    labels:
      - "com.centurylinklabs.watchtower.enbale=true"
    networks:
      movie-network:

  appgateway:
    image: chinhval/movieticketappgateway
    ports:
      - "9000:80"
    environment:
      #- ReverseProxy__Clusters__api-auth__Destinations__destination1__Address=http://identityservermanagement
      - ReverseProxy__Routes__clientapi__ClusterId=client-api
      - ReverseProxy__Routes__clientapi__Match__Path=/client-api/{**remainder}
      - ReverseProxy__Routes__clientapi__Transforms_0__PathRemovePrefix=/client-api
      - ReverseProxy__Routes__clientapi__Transforms_1__PathPrefix=/api
      - ReverseProxy__Routes__clientidentity__ClusterId=client-identity
      - ReverseProxy__Routes__clientidentity__Match__Path=/client-identity/{**remainder}
      - ReverseProxy__Routes__clientidentity__Transforms_0__PathRemovePrefix=/client-identity
      - ReverseProxy__Routes__clientidentity__Transforms_1__PathPrefix=/
      - ReverseProxy__Routes__adminapi__ClusterId=admin-api
      - ReverseProxy__Routes__adminapi__Match__Path=/admin-api/{**remainder}
      - ReverseProxy__Routes__adminapi__Transforms_0__PathRemovePrefix=/admin-api
      - ReverseProxy__Routes__adminapi__Transforms_1__PathPrefix=/api
      - ReverseProxy__Routes__adminui__ClusterId=admin-api
      - ReverseProxy__Routes__adminui__Match__Path=/{**catch-all}
      - ReverseProxy__Routes__signalr__ClusterId=client-api
      - ReverseProxy__Routes__signalr__Match__Path=/room/{**catch-all}
      #- ReverseProxy__Routes__adminui__Transforms_0__PathRemovePrefix=/admin
      - ReverseProxy__Routes__adminui__Transforms_1__PathPrefix=/
      - ReverseProxy__Routes__assets__ClusterId=admin-api
      - ReverseProxy__Routes__assets__Match__Path=/assets/{**remainder}
      - ReverseProxy__Routes__assets__Transforms_0__PathRemovePrefix=/assets
      - ReverseProxy__Routes__assets__Transforms_1__PathPrefix=/assets
      - ReverseProxy__Routes__adminidentity__ClusterId=admin-identity
      - ReverseProxy__Routes__adminidentity__Match__Path=/admin-identity/{**remainder}
      - ReverseProxy__Routes__adminidentity__Transforms_0__PathRemovePrefix=/admin-identity
      - ReverseProxy__Routes__adminidentity__Transforms_1__PathPrefix=/
      - ReverseProxy__Clusters__client-api__Destinations__destination1__Address=http://movieticketclient
      - ReverseProxy__Clusters__client-identity__Destinations__destination1__Address=http://identityserverclient
      - ReverseProxy__Clusters__admin-api__Destinations__destination1__Address=http://movieticketmanagement:80
      - ReverseProxy__Clusters__admin-identity__Destinations__destination1__Address=http://identityservermanagement
    
    labels:
      - "com.centurylinklabs.watchtower.enbale=true"
        
    depends_on:
      - movieticketclient
      - identityserverclient
      - movieticketmanagement
      - identityservermanagement
    networks:
      movie-network:

volumes:
  data:
networks:
  movie-network:
