package com.superman.movieticket.core.repository

import com.superman.movieticket.domain.entities.EntityBase

public interface IRootRepository<TEntity : EntityBase>
{
}

public interface IRepository<TEntity : EntityBase> : IRootRepository<TEntity> {

}
public interface IGridRepository<TEntity : EntityBase> : IRootRepository<TEntity> {

}
