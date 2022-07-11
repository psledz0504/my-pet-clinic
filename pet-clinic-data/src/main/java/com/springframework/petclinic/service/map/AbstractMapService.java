package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.BaseEntity;
import com.springframework.petclinic.service.CrudService;

import java.util.*;

public abstract class AbstractMapService <T extends BaseEntity, ID extends Long>{
    protected Map<Long, T> map = new HashMap<>();

    public Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    public T findById(ID id){
        return map.get(id);
    }

    public T save(T t){
        if(t != null){
            if(t.getId() == null){
                t.setId(getNextId());
            }
            map.put((ID) t.getId(), t);
        }else{
            throw new RuntimeException("Object cannot be null");
        }
        return t;
    }

    public void deleteById(ID id){
        map.remove(id);
    }

    public void delete(T t){
        map.entrySet().removeIf(
                (x) -> {return x.getValue().equals(t);}
        );
    }

    private Long getNextId(){
        Long nextId = null;
        try {
            nextId = Collections.max(map.keySet()) + 1;
        } catch (NoSuchElementException e) {
            nextId = 1L;
        }

        return nextId;
    }

}
