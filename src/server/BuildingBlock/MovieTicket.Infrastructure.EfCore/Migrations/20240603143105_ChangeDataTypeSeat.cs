using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MovieTicket.Infrastructure.EfCore.Migrations
{
    /// <inheritdoc />
    public partial class ChangeDataTypeSeat : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<string>(
                name: "RowNumber",
                schema: "MovieTicket",
                table: "Seats",
                type: "nvarchar(1)",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");

            migrationBuilder.AlterColumn<string>(
                name: "ColNumber",
                schema: "MovieTicket",
                table: "Seats",
                type: "nvarchar(1)",
                nullable: false,
                oldClrType: typeof(int),
                oldType: "int");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<int>(
                name: "RowNumber",
                schema: "MovieTicket",
                table: "Seats",
                type: "int",
                nullable: false,
                oldClrType: typeof(string),
                oldType: "nvarchar(1)");

            migrationBuilder.AlterColumn<int>(
                name: "ColNumber",
                schema: "MovieTicket",
                table: "Seats",
                type: "int",
                nullable: false,
                oldClrType: typeof(string),
                oldType: "nvarchar(1)");
        }
    }
}
