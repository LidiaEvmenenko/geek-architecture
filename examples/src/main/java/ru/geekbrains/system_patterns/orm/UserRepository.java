package ru.geekbrains.system_patterns.orm;

import java.sql.Connection;
import java.util.Optional;

public class UserRepository {
    private final UserMapper mapper;
    private UnitOfWork unitOfWork;
    private final Connection conn;

    public UserRepository(Connection conn) {
        this.conn = conn;
        this.mapper = new UserMapper(this.conn);
        this.unitOfWork = new UnitOfWork(conn);
    }
    public Optional<User> findById(long id){
        return mapper.findId(id);
    }
    public void beginTransaction(){
        this.unitOfWork = new UnitOfWork(this.conn);
    }
    public void insert(User user){
        unitOfWork.registerNew(user);
    }
    public void update(User user){
        unitOfWork.registerUpdate(user);
    }
    public void delete(User user){
        unitOfWork.registerDelete(user);
    }
    public void commitTransaction(){
        unitOfWork.commit();
        mapper.updateIdentityMap(unitOfWork.getUpdateUsers());
        mapper.clearIdentityMapDelete(unitOfWork.getDeleteUsers());
        beginTransaction();
    }
}
