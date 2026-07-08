package team5427.frc.robot.subsystems.endeffector;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class EndEffectorSubsystem extends SubsystemBase {

    private static EndEffectorSubsystem m_instance;

    public static EndEffectorSubsystem getInstance() {
        return (m_instance == null) ? new EndEffectorSubsystem() : m_instance;
    }

    private EndEffectorSubsystem() {

    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        super.periodic();
    }


}
