---
title: The A-racing Maze
author: Josh Chen (chenj23@myumanitoba.ca)
date: Fall 2026
---

# Domain model

## Resources

* https://en.wikipedia.org/wiki/Read%E2%80%93eval%E2%80%93print_loop
* https://medium.com/@itay.gold/java-record-vs-interface-10fb193c14df
* https://help.bizzdesign.com/articles/#!horizzon-help/setting-the-aggregation-kind-of-a-uml-association-relationship
* https://contentlabstudy.com/soft/c-record-methods/
* https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
* https://www.geeksforgeeks.org/java/how-to-solve-java-lang-classnotfoundexception-in-java/

## Diagram

For the domain model, the Maze class acts as a main game object, holding each of the other instances in it such as walls, guards, assignments, and the player. It stores its own width and height, and ensures that at least one of the required instances exists, and that there is only one exit wall. The walls can be either solid or an exit. The player class contains all of its actions, ensures that it stays within the bounds of the map, and has an inventory for holding assignments and save-for-later powerups (the SpeedBoost). The open diamonds represent an aggregation, the closed diamonds represent a composition, and the arrowheads represent an implementation. Here is the diagram for my domain model:

```mermaid
classDiagram
direction TB
    class Maze {
	    -positiveWholeNumber width
	    -positiveWholeNumber height
		-List~Guards~ guards
		-Player player
		-List~Wall~ walls
		-List~Assignment~ assignments
		-List~Powerup~ powerups
    }

	class Coordinate {
		-positiveWholeNumber x
		-positiveWholeNumber y
	}

    class Guard {
	    -Coordinate position
		-positiveWholeNumber speed
    }

    class Powerup {
		-Coordinate position
		+applyPowerup()
    }

    class Wall {
	    -WallType type
		-Coordinate position
    }

    class WallType {
	    WALL
	    EXIT
    }

    class InstantExtraLife {
		+addLife(Player player)
    }

    class Player {
	    -Coordinate position
		-positiveWholeNumber speed
		-positiveWholeNumber lives
	    -List~SpeedPotion~ inventory
		-List~Assignment~ assignmentInventory
	    +move(Player player) nothing 
	    +applyPowerup(Powerup powerup) nothing
		+doAssignment(Assignment assignment) passOrFail
		+completeSet(List~Assignment~ assignmentInventory) : applyPowerup(InstantExtraLife)
    }

    class SpeedPotion { 
	    -positiveWholeNumber speed
		-time duration 
		+increaseSpeed(Player player, speed)
    }

    class AssignmentSet {
		-text title 
		-List~Assignment~ assignmentsInSet
		-positiveWholeNumber assignmentsCompleted
    }

    class Assignment {
		-text title
		-trueOrFalse completed
		-Coordinate position
    }
	
	class Move {
		+moveUp(position, speed) newXCoordinate
		+moveDown(position, speed) newXCoordinate
		+moveLeft(position, speed) newYCoordinate
		+moveRight(position, speed) newYCoordinate
	}

	<<Record>> Wall
	<<Enumeration>> WallType
	<<Interface>> Powerup
	<<Record>> InstantExtraLife
	<<Record>> SpeedPotion
	<<Interface>> Move

	note for Maze "class invariants:<br>width &gt;= 1<br>height &gt;= 1<br>guards.length &gt;= 1<br>player != null<br>exit != null<br>assignments.length &gt;= 1"
	note for Wall "class invariants:<br>type != null"
	note for Player "class invariants:<br>positionX &gt;= 0<br style='--tw-border-spacing-y: 0; --tw-translate-x: 0; --tw-translate-y: 0; --tw-rotate: 0; --tw-skew-x: 0; --tw-skew-y: 0; --tw-scale-x: 1; --tw-scale-y: 1; --tw-pan-x: ; --tw-pan-y: ; --tw-pinch-zoom: ; --tw-scroll-snap-strictness: proximity; --tw-gradient-from-position: ; --tw-gradient-via-position: ; --tw-gradient-to-position: ; --tw-ordinal: ; --tw-slashed-zero: ; --tw-numeric-figure: ; --tw-numeric-spacing: ; --tw-numeric-fraction: ; --tw-ring-inset: ; --tw-ring-offset-width: 0px; --tw-ring-offset-color: ﬂ°fff¶ß --tw-ring-color: ﬂ°3b82f680¶ß --tw-ring-offset-shadow: 0 0 ﬂ°°0000¶ß --tw-ring-shadow: 0 0 ﬂ°°0000¶ß --tw-shadow: 0 0 ﬂ°°0000¶ß --tw-shadow-colored: 0 0 ﬂ°°0000¶ß --tw-blur: ; --tw-brightness: ; --tw-contrast: ; --tw-grayscale: ; --tw-hue-rotate: ; --tw-invert: ; --tw-saturate: ; --tw-sepia: ; --tw-drop-shadow: ; --tw-backdrop-blur: ; --tw-backdrop-brightness: ; --tw-backdrop-contrast: ; --tw-backdrop-grayscale: ; --tw-backdrop-hue-rotate: ; --tw-backdrop-invert: ; --tw-backdrop-opacity: ; --tw-backdrop-saturate: ; --tw-backdrop-sepia: ; --tw-contain-size: ; --tw-contain-layout: ; --tw-contain-paint: ; --tw-contain-style: ;'>positionY &gt;=0<br>positionX &lt;= Maze.width<br>positionY &lt;= Maze.height<br>lives &gt;= 0<br>"
	note for Guard "class invariants:<br>positionX &gt;= 0<br>positionY &gt;=0<br>positionX &lt;= Maze.width<br>positionY &lt;= Maze.height"
	note for AssignmentSet "class invariants:<br>title != null<br>assignmentsCompleted <= assignmentsInSet.length"
	note for Assignment "class invariants:<br>type != null<br>type == COMP2450_A1 or COMP2450_A2"
	note for Coordinate "class invariants:<br>x <= Maze.width<br>y <= Maze.height"

    Maze --* Wall
    Maze --* AssignmentSet
    Maze --* Player
    Maze --* Guard
	Maze --* Powerup
    Wall --* WallType
    Powerup <-- SpeedPotion
    Powerup <-- InstantExtraLife
    Player --* SpeedPotion
	Player --* AssignmentSet
	Player --> Move
	Guard --> Move
    AssignmentSet --* Assignment
	Player --o Coordinate
	Guard --o Coordinate
	Powerup --o Coordinate
	AssignmentSet --o Coordinate
	Wall --o Coordinate
```