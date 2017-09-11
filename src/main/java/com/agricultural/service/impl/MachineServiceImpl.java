package com.agricultural.service.impl;

import com.agricultural.dao.machinesunit.MachinesDAO;
import com.agricultural.dao.machinesunit.MachinesDAOImpl;
import com.agricultural.domains.main.MachineTractorUnit;
import com.agricultural.service.MachineService;

import java.util.ArrayList;

/**
 * Created by Alexey on 11.09.2017.
 */
public class MachineServiceImpl implements MachineService {

    private static MachineServiceImpl instance = new MachineServiceImpl();
    private MachinesDAO machinesDAO = MachinesDAOImpl.getInstance();

    private MachineServiceImpl() {
    }

    public static MachineServiceImpl getInstance() {
        return instance;
    }

    @Override
    public void createMachine(String machineName) {
        machinesDAO.createMachine(machineName);
    }

    @Override
    public void deleteMachine(MachineTractorUnit machineTractorUnit) {
        machinesDAO.deleteMachine(machineTractorUnit);
    }

    @Override
    public void editMachine(MachineTractorUnit machineTractorUnit) {
        machinesDAO.editMachine(machineTractorUnit);
    }

    @Override
    public ArrayList<MachineTractorUnit> getMachines() {
        return machinesDAO.getMachines();
    }

    @Override
    public String[] getAllMachinesName() {
        return machinesDAO.getAllMachinesName();
    }

    @Override
    public MachineTractorUnit getMachineByName(String machineName) {
        return machinesDAO.getMachineByName(machineName);
    }
}
