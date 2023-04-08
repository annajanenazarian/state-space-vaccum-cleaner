# Multi-Agent Adversarial Vacuum Cleaner Agent

Multi-Agent Adversarial Vacuum Cleaner Agent in Java
This project implements a multi-agent adversarial vacuum cleaner agent using Java. The vacuum cleaner agent competes against an opponent agent to clean up as much dirt as possible within a limited time frame. The three algorithms implemented are Minimax, Alpha Beta Pruning, and Expectimax.

![Von Neuman Neighbord] (https://github.com/annajanenazarian/state-space-vaccum-cleaner/blob/main/one.png)

#Algorithms
##Minimax
The Minimax algorithm is a decision-making algorithm that determines the best move for a player by considering all possible future states of the game. In this implementation, the algorithm calculates the optimal move for the player by recursively evaluating the game tree and choosing the move that leads to the highest score.

##Alpha Beta Pruning
The Alpha Beta Pruning algorithm is an optimization of the Minimax algorithm that eliminates unnecessary calculations by pruning branches of the game tree that do not affect the final result. This algorithm reduces the number of nodes that need to be evaluated by evaluating only the nodes that have not yet been pruned.

##Expectimax
The Expectimax algorithm is a decision-making algorithm that determines the best move for a player by considering all possible future states of the game and their probabilities. This algorithm is used when there is uncertainty in the game, such as in games with chance elements or hidden information. In this implementation, the algorithm calculates the expected value of each move by taking the average of the possible outcomes weighted by their probabilities.

![GUI] (https://example.com/image.jpg](https://github.com/annajanenazarian/state-space-vaccum-cleaner/blob/main/two.png)
