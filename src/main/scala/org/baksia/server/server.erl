-module(server).

-export([start/0]).

start() ->
  {ok, LSock} = gen_tcp:listen(1111, [binary, {packet, 0}, {active, false}]),
  {ok, Sock} = gen_tcp:accept(LSock),
  {ok, Bin} = do_recv(Sock),
  ok = gen_tcp:close(Sock),
  Bin.

do_recv(Sock) ->
  case gen_tcp:recv(Sock, 0) of
    {ok, B} ->
      io:format(B),
      io:format("~n"),
      gen_tcp:send(Sock, B),
      do_recv(Sock);
    {error, closed} ->
      {ok, farewell}
  end.
	