import SwiftUI
import Shared

extension ContentView {
    enum LoadableLaunches {
        case loading
        case result([RocketLaunch])
        case error(String)
    }
    
    @MainActor
    class ViewModel: ObservableObject {
        @Published var launches = LoadableLaunches.loading
        
        let helper: KoinHelper = KoinHelper()
        
        init() {
            self.loadLaunches(forceReload: false)
        }
        
        func loadLaunches(forceReload: Bool) {
            Task {
                do {
                    self.launches = .loading
                    let launches = try await helper.getLaunches(forceReload: forceReload)
                    self.launches = .result(launches)
                } catch {
                    self.launches = .error(error.localizedDescription)
                }
            }
        }
    }
}

struct ContentView: View {
    @ObservedObject private(set) var viewModel: ViewModel
    
    @State private var showContent = false
    var body: some View {
        NavigationView {
            listView()
                .navigationBarTitle("SpaceX Launches")
                .navigationBarItems(trailing:
                     Button("Reload") {
                        self.viewModel.loadLaunches(forceReload: true)
                    }
                )
            
        }
    }
    
  private func listView() -> AnyView {
        return switch viewModel.launches {
        case .loading:
            AnyView(Text("Loading...").multilineTextAlignment(.center))
        case .result(let launches):
            AnyView(List(launches, id: \.self) { launch in
                RocketLaunchRow(rocketLaunch: launch)
            })
        case .error(let description):
            AnyView(Text(description).multilineTextAlignment(.center))
        }
        
    }
}
