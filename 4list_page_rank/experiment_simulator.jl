include("queue_simulator.jl")

function carry_experiment(
    probes::Int,
    u::Float64,
    l::Float64,
    max_time::Int
    )
    calculated = calculate_distribution(u, l)
    results = fill(0, 10000)
    for i = 1:probes
        temp_results = simulate_queue(max_time, u, l)
        results = temp_results + results
    end

    results /= probes

    println("Wyniki z modeulu bez ograniczonego rozmiaru kolejki")
    println("Ilość prób: $n")
    println("Czas: $max_time")
    @printf("%5s | %20s | %10s\n", "n", "Eksperymentalne",  "Teoretyczne")
    for i = 1:50
        @printf("-")
    end
    println()

    for i = 1:20
        @printf("%5d | %20.5f | %0.5f\n", i, results[i],  calculated[i])
    end

    errors = [abs(results[ i ] - calculated[ i ])/calculated[ i ] for i = 1:10000]
    (max_error, i) = findmax(errors)

    println("Maksymalny błąd: $max_error")
    println("Wystąpił na pozycji: $i")
    @printf("\t%.10f ", results[i] )
    @printf("\t%.10f\n", calculated[i] )
    println()

end

function carry_experiment(
        n::Int,
        probes::Int,
        u::Float64,
        l::Float64,
        max_time::Int
    )
    calculated = calculate_distribution(n, u, l)
    results = fill(0, n)

    for i = 1:probes
        temp_results = simulate_queue(n, max_time, u, l)
        results = temp_results + results
    end

    results /= probes

    println("Wyniki z modeulu z ograniczonym rozmiarem kolejki")
    println("Rozmiar kolejki: $n")
    println("Ilość prób: $n")
    println("Czas: $max_time")
    @printf("%5s | %20s | %10s\n", "n", "Eksperymentalne",  "Teoretyczne")
    for i = 1:50
        @printf("-")
    end
    println()

    for i = 1:20
        @printf("%5d | %20.5f | %0.5f\n", i, results[i],  calculated[i])
    end

    errors = [abs(results[ i ] - calculated[ i ])/calculated[ i ] for i = 1:n]
    (max_error, i) = findmax(errors)

    println("Maksymalny błąd: $max_error")
    println("Wystąpił na pozycji: $i")
    @printf("\t%.10f ", results[i] )
    @printf("\t%.10f\n", calculated[i] )
    println()
end
