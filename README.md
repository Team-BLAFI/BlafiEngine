<a name="top"></a>

<h1 align="center">BlafiEngine</h1>


<!-- TABLE OF CONTENTS -->

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#concept">Concept</a></li>
      </ul>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
      <ul>
        <li><a href="#group-recordings">Group Recordings</a></li>
      </ul>
    </li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- About the Project -->
## About the Project

Our video game project, titled 'Blafi Adventure,' represents the culmination of our efforts in the challenging realm of game development. Developed using Java, Blafi Adventure stands out as a captivating roguelike that seamlessly combines strategy and excitement. This project was conceived and brought to life as part of our participation in a video game class, where we honed our skills and applied theoretical knowledge to create an engaging gaming experience. From the intricately designed levels to the dynamically evolving gameplay, Blafi Adventure showcases our team's dedication to crafting a memorable and immersive gaming adventure.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- CONCEPT -->
### Concept
### Roguelike Game
* The level design of this game works by moving through rooms.  Each time you enter a new room[Square in Design] you will be faced with a certain number of enemies & 2 exits, leading to another room with the same criteria.
* Rooms aren’t the same, layout’s are different.
* Similar to the Binding of Isaac
* Exit Options:
    * Each exit has a picture on the door showing what reward they can receive in that room.
        * Chest:
            * Offers items/weapons that can make you stronger
        * Currency:
            * Can be used at the shops for more items/weapons
            * Collected in special ways
                * Drops from enemies(disappearing if not collected)
                * Room choices (more stats in one room, more money in another)
                * Killstreak bonuses, damageless bonus, room clear speed bonus, challenge bonus
        * Shop:
                * Leads to [Special Room]:
        * Health:
                * Heals the player

* Rooms Options:
  * [Shop]:
    * Can’t attack in this room
    * NPC that is unkillable
    * Offers said items/weapons
  * [Combat]:
    * Randomized layout that picks a random design based on a number of options
    * Randomized enemy types
  * [Boss Room]:
    * Milestone Room with Boss. 
    * If won, continue with permanent upgrades.
    * Enemies in progressing rooms get stronger.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- BUILT WITH -->
### Built With

[![Java][Java]][Java-url]
 
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GROUP RECORDINGS -->
### Group Recordings
- [Playlist Containing our recordings](https://youtube.com/playlist?list=PL2FT0wTSgerJDwbeJbKeMQKlI-ZbJACYl&si=NOlrMTp2ogXtswpH)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] Engine
  - [x] MouseListener
  - [x] Collision Interface
  - [x] Sprite Manager
- [x] Entity
- [x] Character
  - [x] Controls
  - [x] Attacks
    - [x] Weapons 
- [x] Enemies
  - [x] AI
    - [x] follow player
    - [x] attack player
  - [x] Type
    - [x] Melee
    - [ ] Long ranged
- [x] Level
  - [x] Room Designs
- [x] Art
  - [x] Menu Sprites
  - [x] Player Sprites
  - [x] Enemy Sprites         

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

This project, 'Blafi Adventure,' is created solely for educational purposes within the context of coursework at Lehman College. All components, including code, graphics, and design elements, are intended for academic use and learning, and may not be utilized for commercial purposes without explicit permission from the creators

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

We would like to give credit to

* [itch.io](https://itch.io/game-assets)



[Java]: https://raw.githubusercontent.com/github/explore/5b3600551e122a3277c2c5368af2ad5725ffa9a1/topics/java/java.png
[Java-url]: https://www.java.com/en/
