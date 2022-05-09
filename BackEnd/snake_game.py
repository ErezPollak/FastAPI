import time

from point import Point
from player import Player


class SnakeGame:

    def __init__(self):
        player1 = Player()
        player2 = Player()
        self.players = (player1, player2)
        self.food = Point(9, 1)
        self.function_matrix = [
            [player1.set_direction], [player2.set_direction]
        ]

    def update_state(self):
        for player in self.players:
            player.move()
            if player.head == self.food:
                player.append_tail()

    def get_state(self):

        state = {}

        for i, player in enumerate(self.players):
            state[f"player{i}"] = {"head": str(player.head), "tail": str(player.tail)}

        return state

    def operation_from_function_matrix(self, user, function, param):
        self.function_matrix[user][function](param)
