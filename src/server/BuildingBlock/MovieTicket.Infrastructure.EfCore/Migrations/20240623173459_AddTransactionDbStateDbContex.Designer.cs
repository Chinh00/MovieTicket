﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using MovieTicket.Infrastructure.EfCore;

#nullable disable

namespace MovieTicket.Infrastructure.EfCore.Migrations
{
    [DbContext(typeof(AppBaseContext))]
    [Migration("20240623173459_AddTransactionDbStateDbContex")]
    partial class AddTransactionDbStateDbContex
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "8.0.5")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder);

            modelBuilder.Entity("CategoryMovie", b =>
                {
                    b.Property<Guid>("CategoriesId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("MoviesId")
                        .HasColumnType("uniqueidentifier");

                    b.HasKey("CategoriesId", "MoviesId");

                    b.HasIndex("MoviesId");

                    b.ToTable("CategoryMovie", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Category", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<string>("Name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.ToTable("Categories", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Movie", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<string>("Avatar")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<string>("Description")
                        .HasColumnType("nvarchar(max)");

                    b.Property<string>("Name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("ReleaseDate")
                        .HasColumnType("datetime2");

                    b.Property<long>("TotalTime")
                        .HasColumnType("bigint");

                    b.Property<string>("Trailer")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.ToTable("Movies", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Reservation", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<long>("ItemPrice")
                        .HasColumnType("bigint");

                    b.Property<int>("ReservationState")
                        .HasColumnType("int");

                    b.Property<Guid>("ScreeningId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<long>("TotalPrice")
                        .HasColumnType("bigint");

                    b.Property<string>("TransactionId")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.Property<Guid>("UserId")
                        .HasColumnType("uniqueidentifier");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.HasIndex("ScreeningId");

                    b.ToTable("Reservations", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Room", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<int>("RoomNumber")
                        .HasColumnType("int");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.ToTable("Rooms", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Screening", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<Guid>("MovieId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("RoomId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime>("StartDate")
                        .HasColumnType("datetime2");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.HasIndex("MovieId");

                    b.HasIndex("RoomId");

                    b.ToTable("Screenings", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Seat", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<string>("ColNumber")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<Guid>("RoomId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<string>("RowNumber")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.HasIndex("RoomId");

                    b.ToTable("Seats", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.SeatReservation", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<Guid?>("ReservationId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid?>("SeatId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.HasIndex("ReservationId");

                    b.HasIndex("SeatId");

                    b.ToTable("SeatReservations", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Service", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<string>("Avatar")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<string>("Name")
                        .HasColumnType("nvarchar(max)");

                    b.Property<double>("PriceUnit")
                        .HasColumnType("float");

                    b.Property<string>("Unit")
                        .HasColumnType("nvarchar(max)");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.ToTable("Services", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.ServiceReservation", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<double>("Price")
                        .HasColumnType("float");

                    b.Property<int>("Quantity")
                        .HasColumnType("int");

                    b.Property<Guid>("ReservationId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<Guid>("ServiceId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.HasIndex("ReservationId");

                    b.HasIndex("ServiceId");

                    b.ToTable("ServiceReservations", "MovieTicket");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Transaction", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uniqueidentifier")
                        .HasDefaultValueSql("(newsequentialid())");

                    b.Property<DateTime>("CreatedDate")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getutcdate())");

                    b.Property<string>("Message")
                        .HasColumnType("nvarchar(max)");

                    b.Property<Guid?>("ReservationId")
                        .HasColumnType("uniqueidentifier");

                    b.Property<long>("Total")
                        .HasColumnType("bigint");

                    b.Property<int>("TransactionState")
                        .HasColumnType("int");

                    b.Property<DateTime?>("UpdatedDate")
                        .HasColumnType("datetime2");

                    b.HasKey("Id");

                    b.HasIndex("Id")
                        .IsUnique();

                    b.ToTable("Transactions", "MovieTicket");
                });

            modelBuilder.Entity("CategoryMovie", b =>
                {
                    b.HasOne("MovieTicket.Domain.Entities.Category", null)
                        .WithMany()
                        .HasForeignKey("CategoriesId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("MovieTicket.Domain.Entities.Movie", null)
                        .WithMany()
                        .HasForeignKey("MoviesId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Reservation", b =>
                {
                    b.HasOne("MovieTicket.Domain.Entities.Screening", "Screening")
                        .WithMany("Reservations")
                        .HasForeignKey("ScreeningId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Screening");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Screening", b =>
                {
                    b.HasOne("MovieTicket.Domain.Entities.Movie", "Movie")
                        .WithMany("Screenings")
                        .HasForeignKey("MovieId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("MovieTicket.Domain.Entities.Room", "Room")
                        .WithMany("Screenings")
                        .HasForeignKey("RoomId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Movie");

                    b.Navigation("Room");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Seat", b =>
                {
                    b.HasOne("MovieTicket.Domain.Entities.Room", "Room")
                        .WithMany("Seats")
                        .HasForeignKey("RoomId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Room");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.SeatReservation", b =>
                {
                    b.HasOne("MovieTicket.Domain.Entities.Reservation", "Reservation")
                        .WithMany("SeatReservations")
                        .HasForeignKey("ReservationId")
                        .OnDelete(DeleteBehavior.NoAction);

                    b.HasOne("MovieTicket.Domain.Entities.Seat", "Seat")
                        .WithMany("SeatReservations")
                        .HasForeignKey("SeatId")
                        .OnDelete(DeleteBehavior.NoAction);

                    b.Navigation("Reservation");

                    b.Navigation("Seat");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.ServiceReservation", b =>
                {
                    b.HasOne("MovieTicket.Domain.Entities.Reservation", "Reservation")
                        .WithMany("ServiceReservations")
                        .HasForeignKey("ReservationId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("MovieTicket.Domain.Entities.Service", "Service")
                        .WithMany("ServiceReservations")
                        .HasForeignKey("ServiceId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Reservation");

                    b.Navigation("Service");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Movie", b =>
                {
                    b.Navigation("Screenings");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Reservation", b =>
                {
                    b.Navigation("SeatReservations");

                    b.Navigation("ServiceReservations");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Room", b =>
                {
                    b.Navigation("Screenings");

                    b.Navigation("Seats");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Screening", b =>
                {
                    b.Navigation("Reservations");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Seat", b =>
                {
                    b.Navigation("SeatReservations");
                });

            modelBuilder.Entity("MovieTicket.Domain.Entities.Service", b =>
                {
                    b.Navigation("ServiceReservations");
                });
#pragma warning restore 612, 618
        }
    }
}
