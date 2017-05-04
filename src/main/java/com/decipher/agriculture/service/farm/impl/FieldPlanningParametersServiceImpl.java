package com.decipher.agriculture.service.farm.impl;

import java.util.Set;

import com.decipher.agriculture.service.farm.FieldPlanningParametersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.decipher.agriculture.dao.farm.FieldPlanningParametersDao;
import com.decipher.agriculture.data.farm.FieldPlanningParameters;
import com.decipher.view.form.farmDetails.FieldPlanningParametersView;

@Repository
@Transactional
public class FieldPlanningParametersServiceImpl implements
        FieldPlanningParametersService
{

	@Autowired
	private FieldPlanningParametersDao fieldPlanningDAO;

	@Override
	public int save(FieldPlanningParameters fieldPlanningParameters)
	{
		return fieldPlanningDAO.save(fieldPlanningParameters);
	}

	@Override
	public boolean update(FieldPlanningParameters fieldPlanningParameters)
	{
		return fieldPlanningDAO.update(fieldPlanningParameters);
	}

	@Override
	public boolean deleteById(int id)
	{
		return fieldPlanningDAO.deleteById(id);
	}

	@Override
	public boolean saveList(
			Set<FieldPlanningParameters> fieldPlanningParametersList)
	{
		return fieldPlanningDAO.saveList(fieldPlanningParametersList);
	}

	@Override
	public FieldPlanningParametersView getFieldPlanningById(int id)
	{
		return new FieldPlanningParametersView(
				fieldPlanningDAO.getFieldPlanningById(id));
	}

}
