using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace MovieTicketClient.IdentityServer.Migrations
{
    /// <inheritdoc />
    public partial class AddPropertyGender : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "UserGender",
                table: "Users",
                type: "int",
                nullable: true);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "UserGender",
                table: "Users");
        }
    }
}
