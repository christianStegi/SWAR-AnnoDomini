# AnnoDominiScala

A virtual Version of the Cardgame Anno Domini written in Scala
Created for a Software Engineering course

More info about the game:
https://fatamorgana.ch/fatamorgana/annodomini


### Game Rules:
Anno Domini is a cardgame about guessing the order of historical events.
* The frontside of the cards contains a brief description of a historical event, the backside contains the year the event happend in.
* The players are not allowed to look at the backside, so they only see the event not what year they happend in.
* At the beginning every player is given 5 cards, and one card is placed in the middle of the table.
* The current player can either place a card or doubt the current cardorder.
* If the player decides to place a card, they have to decide how their card relates to the order of the cards allready on the table.
* Older events get to be placed above younger ones (the event on top is the oldest, while the event at the buttom is the most recent)
* If the player chooses to doubt the current cardorders, the backside of all the cards on the table get revealed.
* If the cards are in order, the doubting player gets punished by having to draw 2 Cards and the turn goes to the next player.
* If the cards are out of oder, the previous player gets punished by having to draw 3 Cards, even if they didn't place the cards that are out of order.
* The winner is the first person to get rid of all their cards

It may sound like this game is just about knowledge of historical events, but while that helps, it is actually more about making good guesses and proper bluffing.
You don't have to know in what year the Romans invaded the British isles, just that it probably happeneded before the Rolling Stones where founded.


### Notes about current implemenatation

Currently only a testversion of the game has been implemented, it has all the needed functionality but the game consits of testcards instead of proper playing cards.
The text of the testcards doesn't contain a historical event, but "" 