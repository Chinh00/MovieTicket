namespace MovieTicket.Infrastructure.Files;

public interface IFileHelper
{
    public Task<string> SaveFile(IFormFile file, string folder);

    public Task<bool> RemoveFile(string folder, string fileName);

    public Task<bool> RemoveFolder(string folder);
}