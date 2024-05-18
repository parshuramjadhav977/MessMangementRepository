package org.messmanagement.admin.repository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.messmanagement.admin.DBConfig.DBConfig;
import org.messmanagement.admin.model.RegistrationModel;

public class RegistrationRepositoryImpl extends DBConfig implements RegistrationRepositroy{
    List<Object[]> list;
    List<Integer> loginlist;
	@Override
	public List<Integer> validateLogin(String username, String password) {
		try {
			stmt=conn.prepareStatement("select rid,roleid from registration where email=? and password=?");
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs=stmt.executeQuery();
			loginlist=new ArrayList<>();
			if(rs.next()) {
				loginlist.add(rs.getInt(1));
				loginlist.add(rs.getInt(2));
				return loginlist.size()>0?loginlist:null;
			}else {
				return null;
			}
		}catch(Exception e) {
			System.out.println("Error is "+e);
			return null;
		}
	}

	@Override
	public boolean addRegistration(RegistrationModel model) {
		try {
			//int cid = getCategoryIDByName(category);
			stmt = conn.prepareStatement("insert into registration values('0',?,?,?,?,?,?,?,?,?,2)");
			stmt.setString(1, model.getName());
			stmt.setString(2, model.getContact());
			stmt.setString(3, model.getAddress());
			stmt.setDate(4, (Date) model.getRsdate());
			stmt.setDate(5, (Date) model.getRedate());
			stmt.setInt(6, model.getAmount());
			stmt.setString(7, model.getUsername());
			stmt.setString(8, model.getPassword());
			stmt.setInt(9, model.getCid());
			int value = stmt.executeUpdate();
			return value>0?true:false;

		} catch (Exception e) {
			System.err.println("Error is " + e);
			return false;
		}
	}

	@Override
	public List<Object[]> getAllRegistration() {
		try {
			list=new ArrayList<>();
			stmt=conn.prepareStatement("select r.rid,r.name,r.contact,r.address,r.rsdate,r.redate,r.amount,r.email,r.password,c.category,rr.rolename from registration r left join category c on r.cid=c.cid left join roles rr on r.roleid=rr.roleid");
			rs=stmt.executeQuery();
			while(rs.next()) {
				Object []obj=new Object[] {rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getDate(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)};
				list.add(obj);
				
			}
			return list.size()>0?list:null;
		}catch(Exception e) {
			System.out.println("Error is "+e);
			return null;
		}
	}

	@Override
	public boolean deleteRegistration(int rid) {
		try {
			stmt=conn.prepareStatement("delete from registration where rid=?");
			stmt.setInt(1, rid);
			int value=stmt.executeUpdate();
			return value>0?true:false;
		}catch(Exception e) {
			System.out.println("Error is "+e);
			return false;
		}
	}

	@Override
	public boolean updateRegistration(int rid, String name, String contact, String address, Date rsdate, Date redate,
			int amount, String username, String password, int cid, int roleid) {
		try {
		stmt=conn.prepareStatement("update registration set name=?, contact=?, address=?, rsdate=?, redate=?, amount=?, email=?, password=?, cid=?, roleid=? where rid=?");
		stmt.setString(1, name);
		stmt.setString(2, contact);
		stmt.setString(3, address);
		stmt.setDate(4, rsdate);
		stmt.setDate(5, redate);
		stmt.setInt(6, amount);
		stmt.setString(7, username);
		stmt.setString(8, password);
		stmt.setInt(9, cid);
		stmt.setInt(10, roleid);
		stmt.setInt(11, rid);
		int value=stmt.executeUpdate();
		return value>0?true:false;
	}catch(Exception e) {
		System.out.println("Error is "+e);
		return false;
	}
	
	
	}
    List<RegistrationModel> rlist;
	@Override
	public List<RegistrationModel> getNameByRid(int rid) {
		try {
			rlist=new ArrayList<>();
			stmt=conn.prepareStatement("select * from registration where rid=?");
			stmt.setInt(1, rid);
			rs=stmt.executeQuery();
			if(rs.next()) {
				RegistrationModel rmodel=new RegistrationModel();
				rmodel.setRid(rs.getInt(1));
				rmodel.setName(rs.getString(2));
				rmodel.setContact(rs.getString(3));
				rmodel.setAddress(rs.getString(4));
				rmodel.setRsdate(rs.getDate(5));
				rmodel.setRedate(rs.getDate(6));
				rmodel.setAmount(rs.getInt(7));
				rmodel.setUsername(rs.getString(8));
				rmodel.setPassword(rs.getString(9));
				rmodel.setCid(rs.getInt(10));
				rlist.add(rmodel);
				return rlist.size()>0?rlist:null;
			}else {
				return null;
			}
		}catch(Exception e) {
			System.out.println("Error is "+e);
			return null;
		}
	}

	@Override
	public boolean updateProfile(int rid, String name, String contact, String address, String email, String password) {
		try {
			stmt=conn.prepareStatement("update registration set name=? ,contact=? ,address=? ,email=? ,password=? where rid=?");
		    stmt.setString(1, name);
		    stmt.setString(2, contact);
		    stmt.setString(3, address);
		    stmt.setString(4, email);
		    stmt.setString(5, password);
		    stmt.setInt(6, rid);
		    int value=stmt.executeUpdate();
		    return value>0?true:false;
		}catch(Exception e) {
			System.out.println("Error is "+e);
			return false;
		}
	}
    List<Object[]> clist;
	@Override
	public List<Object[]> getAllRegistrationByCategory(String cat) {
		try {
			clist=new ArrayList<>();
			stmt=conn.prepareStatement("select r.rid,r.name,r.contact,r.address,r.rsdate,r.redate,r.amount,r.email,r.password,c.category,rr.rolename from registration r left join category c on r.cid=c.cid left join roles rr on r.roleid=rr.roleid where category like '%"+cat+"%'");
			rs=stmt.executeQuery();
			while(rs.next()) {
				Object []obj=new Object[] {rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getDate(6),rs.getInt(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)};
				clist.add(obj);
				
			}
			return clist.size()>0?clist:null;
		}catch(Exception e) {
			System.out.println("Error is "+e);
			return null;
		}
	}
}
