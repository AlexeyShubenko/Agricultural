package com.agricultural.dao.machinesunit;

import com.agricultural.domains.main.MachineTractorUnit;
import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public interface MachinesDAO {

    void createMachine(String machineName);
    void deleteMachine(MachineTractorUnit machineTractorUnit);
    void editMachine(MachineTractorUnit tractor);
    ArrayList<MachineTractorUnit> getMachines();
    String[] getAllMachinesName();
    MachineTractorUnit getMachineByName(String machineName);

}
