using Plots

function visualize(values::Matrix)
    (n, k) = size(values)
    Plots.plot()
    for i = 1:n
        Plots.plot!([values[i, j] for j = 1:k])
    end
    Plots.plot!()
end

function visualize(values::Matrix, filename::String)
    visualize(values)
    png(string("./temp/", filename))
end
