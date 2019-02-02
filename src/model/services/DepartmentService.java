package model.services;

import model.DAO.DaoFactory;
import model.DAO.DepartmentDao;
import model.entities.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }

}
