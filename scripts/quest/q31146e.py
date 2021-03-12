# Knight Stronghold: Secret Grove
# Quest: Rescue Neinhart

NEINHEART = 2143001

sm.setSpeakerID(NEINHEART)
sm.completeQuest(parentID)
sm.sendNext("Thank you for saving me, but I'll remain here. My"
            "\r\ndissapearance would only make things worse. Besides,"
            "\r\nI should be able to do some good from here.")
sm.sendSay("Please tell Alex my decision.")
sm.sendPrev("And please...stop her. There's no way to get the old"
            "\r\nCygnus back. We only have one option...")
sm.dispose()