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
        Optional<User> userOptional = mapper.findLogin(user.getId(), user.getLogin());
        if(userOptional.isEmpty()){
        unitOfWork.registerNew(user);
        }else {
            System.out.println("User is already exist");
        }
    }
    public void update(User user){
        Optional<User> userOptional = mapper.findId(user.getId());
        if(!userOptional.isEmpty()){
           unitOfWork.registerUpdate(user);
        }else {
            System.out.println("User id is not found");
        }
    }
    public void delete(User user){
        Optional<User> userOptional = mapper.findId(user.getId());
        if(!userOptional.isEmpty()){
        unitOfWork.registerDelete(user);
        }else {
            System.out.println("User id is not found");
        }
    }
    public void commitTransaction(){
        unitOfWork.commit();
        mapper.updateIdentityMap(unitOfWork.getUpdateUsers());
        mapper.clearIdentityMapDelete(unitOfWork.getDeleteUsers());
        beginTransaction();
    }
}
