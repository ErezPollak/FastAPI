import time

from point import Point
from player import Player


class SnakeGame:

    def __init__(self):
        player1 = Player(Point(2, 3))
        player2 = Player(Point(6, 7))
        self.players = (player1, player2)
        self.food = Point(11, 1)
        self.function_matrix = [
            [player1.set_direction], [player2.set_direction]
        ]

    def update_state(self):
        for player in self.players:
            player.move()
            if player.head == self.food:
                player.append_tail()
                self.food.rand_point()

    def get_state(self):

        state = {"food": str(self.food)}

        players_states = []

        for player in self.players:
            player_dict = {"head": str(player.head),
                           "tail": str(player.tail)[1:-1].replace(",", "")}
            players_states.append(player_dict)

        state["players"] = players_states

        return state

    def operation_from_function_matrix(self, user, function, param):
        self.function_matrix[user][function](param)

#
# state = {
#
#     food: "9 4"
#
#     players : [
#         {
#             head: "3 4"
#             tail: "0 1 2 3 2 1 1 1 1 1 1 1 1 0 0 0 0 "
#         },
#         {
#             head:"3 1"
#             tail: "1 2 2 2 2 2 2 2 3 3 3 3 3 0 0 0 0"
#         }
#
#     ]
#
# }
