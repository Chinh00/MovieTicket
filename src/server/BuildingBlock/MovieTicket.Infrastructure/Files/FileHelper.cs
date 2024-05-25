using MovieTicket.Infrastructure.Security;

namespace MovieTicket.Infrastructure.Files;

public class FileHelper : IFileHelper
{
    private readonly IServerAccessor _serverAccessor;
    private readonly string _webRootPath;

    public FileHelper(IWebHostEnvironment webHostEnvironment, IServerAccessor serverAccessor)
    {
        _serverAccessor = serverAccessor;
        _webRootPath = webHostEnvironment.WebRootPath;
    }

    public async Task<string> SaveFile(IFormFile file, string folder)
    {
        CheckDirectory(folder);

        var fileName = file.FileName.Replace(" ", "_");

        var filePath = Path.Combine(BaseDirectory.Files, folder, fileName);

        await using var output = File.Create(Path.Combine(_webRootPath, filePath));
        await file.CopyToAsync(output);

        return $"/{filePath}";
    }

    public Task<bool> RemoveFile(string folder, string fileName)
    {
        var filePath = Path.Combine(_webRootPath, BaseDirectory.Files, folder, fileName.Replace(" ", "_"));
        if (!File.Exists(filePath)) return Task.FromResult(false);

        File.Delete(filePath);
        return Task.FromResult(true);
    }

    public Task<bool> RemoveFolder(string folder)
    {
        var filePath = Path.Combine(_webRootPath, BaseDirectory.Files, folder);
        if (!Directory.Exists(filePath)) return Task.FromResult(false);

        Directory.Delete(filePath, true);
        return Task.FromResult(true);
    }

    

    private void CheckDirectory(string folder)
    {
        var path = Path.Combine(_webRootPath, BaseDirectory.Files, folder);
        if (!Directory.Exists(path) && !string.IsNullOrEmpty(path))
            Directory.CreateDirectory(path);
    }
}