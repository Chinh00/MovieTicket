using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MovieTicket.Infrastructure.EfCore.Migrations
{
    /// <inheritdoc />
    public partial class RemovePriceMovie : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Price",
                schema: "MovieTicket",
                table: "Movies");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<float>(
                name: "Price",
                schema: "MovieTicket",
                table: "Movies",
                type: "real",
                nullable: false,
                defaultValue: 0f);
        }
    }
}
