using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MovieTicket.Infrastructure.EfCore.Migrations
{
    /// <inheritdoc />
    public partial class ChangeDateType : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Services",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "ServiceReservations",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Seats",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "SeatReservations",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Screenings",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Rooms",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Reservations",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Movies",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Categories",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "(getutcdate())",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Services",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "ServiceReservations",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Seats",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "SeatReservations",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Screenings",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Rooms",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Reservations",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Movies",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Categories",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "(getutcdate())");
        }
    }
}
