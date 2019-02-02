package model.DAO.impl;

import db.DB;
import db.DbException;
import model.DAO.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "INSERT INTO Department "
                        + "(Name) "
                        + "VALUES "
                        + "(?)",
                        Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, department.getName());

            int rows = st.executeUpdate();

            if (rows > 0) {

                ResultSet rs = st.getGeneratedKeys();

                if (rs.next()) {

                    department.setId(rs.getInt(1));

                }

                DB.closeResultSet(rs);

            } else {

                throw new DbException("Unexpected Error! No rows inserted!");

            }

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);

        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "UPDATE department "
                        + "SET Name = ? "
                        + "WHERE Id = ?"
            );

            st.setString(1, department.getName());
            st.setInt(2, department.getId());

            st.executeUpdate();

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);

        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {

            st = conn.prepareStatement(
                    "DELETE FROM department "
                        + " WHERE Id = ?"
            );

            st.setInt(1, id);

            st.executeUpdate();

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);

        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet         rs = null;

        try {

            st = conn.prepareStatement(
                    "SELECT * FROM department "
                        + "WHERE Id = ?"
            );

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()) {

                Department department = new Department();

                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));

                return department;

            } else {

                return null;

            }

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet         rs = null;

        try {

            st = conn.prepareStatement(
                    "SELECT * FROM department"
            );

            rs = st.executeQuery();

            List<Department> departmentList = new ArrayList<>();

            while (rs.next()) {

                Department department = new Department();

                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));

                departmentList.add(department);

            }

            return departmentList;

        } catch (SQLException e) {

            throw new DbException(e.getMessage());

        } finally {

            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }
}
