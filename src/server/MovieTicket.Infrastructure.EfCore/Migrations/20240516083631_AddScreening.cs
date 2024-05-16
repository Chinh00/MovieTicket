using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MovieTicket.Infrastructure.EfCore.Migrations
{
    /// <inheritdoc />
    public partial class AddScreening : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Reservations_Screening_ScreeningId",
                schema: "MovieTicket",
                table: "Reservations");

            migrationBuilder.DropForeignKey(
                name: "FK_Screening_Movies_MovieId",
                table: "Screening");

            migrationBuilder.DropForeignKey(
                name: "FK_Screening_Rooms_RoomId",
                table: "Screening");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Screening",
                table: "Screening");

            migrationBuilder.RenameTable(
                name: "Screening",
                newName: "Screenings",
                newSchema: "MovieTicket");

            migrationBuilder.RenameIndex(
                name: "IX_Screening_RoomId",
                schema: "MovieTicket",
                table: "Screenings",
                newName: "IX_Screenings_RoomId");

            migrationBuilder.RenameIndex(
                name: "IX_Screening_MovieId",
                schema: "MovieTicket",
                table: "Screenings",
                newName: "IX_Screenings_MovieId");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                schema: "MovieTicket",
                table: "Screenings",
                type: "datetime2",
                nullable: false,
                defaultValueSql: "getDate()",
                oldClrType: typeof(DateTime),
                oldType: "datetime2");

            migrationBuilder.AlterColumn<Guid>(
                name: "Id",
                schema: "MovieTicket",
                table: "Screenings",
                type: "uniqueidentifier",
                nullable: false,
                defaultValueSql: "(newsequentialid())",
                oldClrType: typeof(Guid),
                oldType: "uniqueidentifier");

            migrationBuilder.AddPrimaryKey(
                name: "PK_Screenings",
                schema: "MovieTicket",
                table: "Screenings",
                column: "Id");

            migrationBuilder.CreateIndex(
                name: "IX_Screenings_Id",
                schema: "MovieTicket",
                table: "Screenings",
                column: "Id",
                unique: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Reservations_Screenings_ScreeningId",
                schema: "MovieTicket",
                table: "Reservations",
                column: "ScreeningId",
                principalSchema: "MovieTicket",
                principalTable: "Screenings",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Screenings_Movies_MovieId",
                schema: "MovieTicket",
                table: "Screenings",
                column: "MovieId",
                principalSchema: "MovieTicket",
                principalTable: "Movies",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Screenings_Rooms_RoomId",
                schema: "MovieTicket",
                table: "Screenings",
                column: "RoomId",
                principalSchema: "MovieTicket",
                principalTable: "Rooms",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Reservations_Screenings_ScreeningId",
                schema: "MovieTicket",
                table: "Reservations");

            migrationBuilder.DropForeignKey(
                name: "FK_Screenings_Movies_MovieId",
                schema: "MovieTicket",
                table: "Screenings");

            migrationBuilder.DropForeignKey(
                name: "FK_Screenings_Rooms_RoomId",
                schema: "MovieTicket",
                table: "Screenings");

            migrationBuilder.DropPrimaryKey(
                name: "PK_Screenings",
                schema: "MovieTicket",
                table: "Screenings");

            migrationBuilder.DropIndex(
                name: "IX_Screenings_Id",
                schema: "MovieTicket",
                table: "Screenings");

            migrationBuilder.RenameTable(
                name: "Screenings",
                schema: "MovieTicket",
                newName: "Screening");

            migrationBuilder.RenameIndex(
                name: "IX_Screenings_RoomId",
                table: "Screening",
                newName: "IX_Screening_RoomId");

            migrationBuilder.RenameIndex(
                name: "IX_Screenings_MovieId",
                table: "Screening",
                newName: "IX_Screening_MovieId");

            migrationBuilder.AlterColumn<DateTime>(
                name: "CreatedDate",
                table: "Screening",
                type: "datetime2",
                nullable: false,
                oldClrType: typeof(DateTime),
                oldType: "datetime2",
                oldDefaultValueSql: "getDate()");

            migrationBuilder.AlterColumn<Guid>(
                name: "Id",
                table: "Screening",
                type: "uniqueidentifier",
                nullable: false,
                oldClrType: typeof(Guid),
                oldType: "uniqueidentifier",
                oldDefaultValueSql: "(newsequentialid())");

            migrationBuilder.AddPrimaryKey(
                name: "PK_Screening",
                table: "Screening",
                column: "Id");

            migrationBuilder.AddForeignKey(
                name: "FK_Reservations_Screening_ScreeningId",
                schema: "MovieTicket",
                table: "Reservations",
                column: "ScreeningId",
                principalTable: "Screening",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Screening_Movies_MovieId",
                table: "Screening",
                column: "MovieId",
                principalSchema: "MovieTicket",
                principalTable: "Movies",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Screening_Rooms_RoomId",
                table: "Screening",
                column: "RoomId",
                principalSchema: "MovieTicket",
                principalTable: "Rooms",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
