using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MovieTicket.Infrastructure.EfCore.Migrations
{
    /// <inheritdoc />
    public partial class RefactorDatabase : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "Services",
                schema: "MovieTicket",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false, defaultValueSql: "(newsequentialid())"),
                    Name = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    Unit = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    PriceUnit = table.Column<double>(type: "float", nullable: false),
                    Avatar = table.Column<string>(type: "nvarchar(max)", nullable: true),
                    CreatedDate = table.Column<DateTime>(type: "datetime2", nullable: false, defaultValueSql: "getDate()"),
                    UpdatedDate = table.Column<DateTime>(type: "datetime2", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_Services", x => x.Id);
                });

            migrationBuilder.CreateTable(
                name: "ServiceReservations",
                schema: "MovieTicket",
                columns: table => new
                {
                    Id = table.Column<Guid>(type: "uniqueidentifier", nullable: false, defaultValueSql: "(newsequentialid())"),
                    ReservationId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    ServiceId = table.Column<Guid>(type: "uniqueidentifier", nullable: false),
                    Quantity = table.Column<int>(type: "int", nullable: false),
                    Price = table.Column<double>(type: "float", nullable: false),
                    CreatedDate = table.Column<DateTime>(type: "datetime2", nullable: false, defaultValueSql: "getDate()"),
                    UpdatedDate = table.Column<DateTime>(type: "datetime2", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_ServiceReservations", x => x.Id);
                    table.ForeignKey(
                        name: "FK_ServiceReservations_Reservations_ReservationId",
                        column: x => x.ReservationId,
                        principalSchema: "MovieTicket",
                        principalTable: "Reservations",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_ServiceReservations_Services_ServiceId",
                        column: x => x.ServiceId,
                        principalSchema: "MovieTicket",
                        principalTable: "Services",
                        principalColumn: "Id",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_ServiceReservations_Id",
                schema: "MovieTicket",
                table: "ServiceReservations",
                column: "Id",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_ServiceReservations_ReservationId",
                schema: "MovieTicket",
                table: "ServiceReservations",
                column: "ReservationId");

            migrationBuilder.CreateIndex(
                name: "IX_ServiceReservations_ServiceId",
                schema: "MovieTicket",
                table: "ServiceReservations",
                column: "ServiceId");

            migrationBuilder.CreateIndex(
                name: "IX_Services_Id",
                schema: "MovieTicket",
                table: "Services",
                column: "Id",
                unique: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "ServiceReservations",
                schema: "MovieTicket");

            migrationBuilder.DropTable(
                name: "Services",
                schema: "MovieTicket");
        }
    }
}
