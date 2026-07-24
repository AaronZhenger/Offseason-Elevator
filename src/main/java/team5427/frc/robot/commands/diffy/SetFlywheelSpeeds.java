package team5427.frc.robot.commands.diffy;

import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.subsystems.diffy.DiffySubsystem;

public class SetFlywheelSpeeds extends Command {
  private DiffySubsystem endEffector;

  private LinearVelocity speed;

  public SetFlywheelSpeeds(LinearVelocity speed) {
    endEffector = DiffySubsystem.getInstance();

    this.speed = speed;
  }

  @Override
  public void initialize() {
    endEffector.setFlywheelSetpoint(speed);
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
    super.end(interrupted);
  }
}
