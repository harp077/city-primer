/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package city.em;

//import javax.ejb.Stateless;
import city.em.AbstractFacade;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import city.jpa.City;

//@Stateless
//@RequestScoped
//@RequestScope
@Scope("prototype")
@Service
@Repository
@Transactional
public class CityFacade extends AbstractFacade<City> {

    @PersistenceContext(unitName = "cityPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CityFacade() {
        super(City.class);
    }
    
}
