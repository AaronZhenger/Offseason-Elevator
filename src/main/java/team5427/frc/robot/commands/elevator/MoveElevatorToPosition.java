package team5427.frc.robot.commands.elevator;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.subsystems.elevator.ElevatorSubsystem;

public class MoveElevatorToPosition extends Command {
    private ElevatorSubsystem elevator;

    private Distance distance;

    public MoveElevatorToPosition(Distance distance) {
        elevator = ElevatorSubsystem.getInstance();

        this.distance = distance;

        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        elevator.setElevatorSetpoint(distance);
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        super.execute();
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return super.isFinished();
    }

    @Override
    public void end(boolean interrupted) {
        // TODO Auto-generated method stub
        super.end(interrupted);
    }
}
