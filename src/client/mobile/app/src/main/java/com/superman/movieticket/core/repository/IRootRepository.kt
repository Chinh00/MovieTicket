package com.superman.movieticket.core.repository

import com.superman.movieticket.domain.entities.EntityBase

interface IRootRepository<TEntity : EntityBase>

interface IRepository<TEntity : EntityBase> : IRootRepository<TEntity>
interface IGridRepository<TEntity : EntityBase> : IRootRepository<TEntity>
