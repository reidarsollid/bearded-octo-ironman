defmodule Server do
	def run do
		{ :ok, lsock } = :gen_tcp.listen( 1111, [ :binary, { :packet, 0 }, { :active, false }])
		{ :ok, sock } = :gen_tcp.accept(lsock)
		{ :ok, bin } = do_recv( sock )
		:ok = :gen_tcp.close(sock)
		bin
	end
	
	def do_recv(sock) do
		case :gen_tcp.recv(sock, 0) do
			{ :ok, b } ->
				IO.puts b;
				:gen_tcp.send(sock, b); 
				do_recv(sock)
			{ :error, :closed } ->
				{ :ok, :farewell }
		end
	end
end
