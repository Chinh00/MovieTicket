using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;

namespace MovieTicket.Core.Repository
{
    public interface IRepository<TEntity> where TEntity : EntityBase
    {
        TEntity FindById(Guid id);
        Task<TEntity> FindOneAsync(ISpecification<TEntity> spec);
        Task<List<TEntity>> FindAsync(ISpecification<TEntity> spec);
        Task<TEntity> AddAsync(TEntity entity);
        Task RemoveAsync(TEntity entity);
    }

    public interface IGridRepository<TEntity> where TEntity : EntityBase
    {
        ValueTask<long> CountAsync(IGridSpecification<TEntity> spec);
        Task<List<TEntity>> FindAsync(IGridSpecification<TEntity> spec);
    }
}
