#include "Arduino.h"
#include "Machine.h"
#include "Motion.h"

#include <mk20dx128.h>

void setup()
{
  pinMode(LED, OUTPUT);

  pinMode(X_STEP, OUTPUT);
  pinMode(X_DIR, OUTPUT);

  pinMode(Y_STEP, OUTPUT);
  pinMode(Y_DIR, OUTPUT);

  Serial.begin(115200);

  //MotionTimer.begin(motion_interupt, 10);

  //set_target_position(targets[target_pointer][0], targets[target_pointer][1], targets[target_pointer][2]);
  //target_pointer++;

  motion_init(2, MIN_FEED_RATE, MAX_LINEAR_VELOCITY);
  motion_init_axis(0, LINEAR_AXIS, X_STEP, X_DIR, 'X', "Inch", X_SCALE, X_ACCEL, X_MAX_VELOCITY);
  motion_init_axis(1, LINEAR_AXIS, Y_STEP, Y_DIR, 'Y', "Inch", Y_SCALE, Y_ACCEL, Y_MAX_VELOCITY);
}
void loop()
{
  if (Serial.available())
  {
    motion_set_target_position("X100Y100", 350, 4);
    Serial.read();
  }
  motion_loop_tick();
}
