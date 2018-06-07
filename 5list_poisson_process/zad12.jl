
include("experiment.jl")
using DataStructures
n = 100
t = 10000
lambda = 0.5
lambdas = [0.5, 0.8, 0.9, 0.99]


function expotential()
    -log(rand())
end

function constant()
    1
end

function carry_experiment(n::Int, t::Int, lambdas::Vector{Float64}, get_random::Function, message::String)
    println(message)
    for lambda in lambdas
        result = carry_experiment(n, t, lambda, get_random)
        @printf("Lambda %.2f : %f\n", lambda, result)
    end
end

carry_experiment(n, t, lambdas, expotential, "Expotential time")
carry_experiment(n, t, lambdas, constant, "Constant time")
