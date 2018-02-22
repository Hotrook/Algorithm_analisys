using Gadfly
include( "leader_algorithms.jl")

u = 500
n = 500
probes = 10000
results = Array{Int64}(probes)

for i = 1:probes
    results[ i ] = leader_secondscenario( u, n )
end

Gadfly.plot( x = results, Geom.histogram )


ev = expected_value(results)
var = variance( results, ev )
ps = success_in_first_round( results, u )

println("Expected value: $ev\n")
println("Variance: $var\n")
println("P of success: $ps\n")
