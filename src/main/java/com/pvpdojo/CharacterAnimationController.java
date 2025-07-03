package com.pvpdojo;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Animation;
import net.runelite.api.AnimationController;
import net.runelite.api.Client;

public class CharacterAnimationController extends AnimationController
{
    public CharacterAnimationController(Client client, int animationID)
    {
        super(client, animationID);
    }

    @Getter
    @Setter
    private boolean loop;

    @Getter
    @Setter
    private boolean finished;

    public boolean isLoop() {
        return loop;
    }

    public boolean isFinished() {
        return finished;
    }

    public CharacterAnimationController(Client client, int animationID, boolean loop)
    {
        super(client, animationID);
        this.loop = loop;
        this.finished = false;
    }

    public CharacterAnimationController(Client client, Animation animation, boolean loop)
    {
        super(client, animation);
        this.loop = loop;
        this.finished = false;

    }

    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
