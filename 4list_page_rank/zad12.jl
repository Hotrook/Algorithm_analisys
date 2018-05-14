include("experiment_simulator.jl")

l = 0.2
u = 0.3
n = 30
probes = 10
max_time = 10000000
results = fill(0.0, 10000)

carry_experiment(probes, u, l, max_time)


carry_experiment(n, probes, u, l, max_time)
