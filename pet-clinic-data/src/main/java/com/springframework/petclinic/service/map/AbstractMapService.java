package com.springframework.petclinic.service.map;

import com.springframework.petclinic.model.BaseEntity;
import com.springframework.petclinic.service.CrudService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public abstract class AbstractMapService <T extends BaseEntity, ID extends Long>{
    protected Map<Long, T> map = new HashMap<>();

    public Set<T> findAll(){
        log.info(String.valueOf(map.size()));
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
            map.put(t.getId(), t);
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
                x -> x.getValue().equals(t)
        );
    }

    private Long getNextId(){
        if (map.isEmpty())
            return 1L;
        else
            return Collections.max(map.keySet()) + 1;
    }

}
