package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.BaseEntity;
import com.springframework.petclinic.service.CrudService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMapService <T extends BaseEntity, ID> implements CrudService<T, ID> {
    protected Map<ID, T> map = new HashMap<>();

    public Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    public T findById(ID id){
        return map.get(id);
    }

    public T save(T t){
        map.put((ID) t.getId(), t);
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

}
