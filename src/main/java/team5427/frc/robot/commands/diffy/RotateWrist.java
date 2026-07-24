package team5427.frc.robot.commands.diffy;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import team5427.frc.robot.subsystems.diffy.DiffySubsystem;

public class RotateWrist extends Command {
  private DiffySubsystem endEffector;

  private Rotation2d rotation;

  public RotateWrist(Rotation2d rotation) {
    endEffector = DiffySubsystem.getInstance();

    this.rotation = rotation;
  }

  @Override
  public void initialize() {
    endEffector.setDifferenceSetpoint(rotation);
  }

  @Override
  public void execute() {
    // TODO Auto-generated method stub
    super.execute();
  }

  @Override
  public boolean isFinished() {
    return endEffector.pivotAtGoal();
  }

  @Override
  public void end(boolean interrupted) {
    // TODO Auto-generated method stub
    super.end(interrupted);
  }
}
